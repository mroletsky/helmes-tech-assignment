import { useEffect, useState } from "react";
import type { SectorTreeDto, UserSectorResponse } from "./types";
import SectorNode from "./SectorNode";

const API_BASE = "/api";

export default function App() {
    const [name, setName] = useState<string>("");
    const [agree, setAgree] = useState<boolean>(false);
    const [sectors, setSectors] = useState<SectorTreeDto[]>([]);
    const [selectedSectorIds, setSelectedSectorIds] = useState<number[]>([]);
    const [error, setError] = useState<string>("");
    const [success, setSuccess] = useState<string>("");

    useEffect(() => {
        fetch(`${API_BASE}/sectors/tree`)
            .then(res => res.json())
            .then((data: SectorTreeDto[]) => setSectors(data))
            .catch(() => setError("Failed to load sectors"));
    }, []);

    const toggleSector = (id: number) => {
        setSelectedSectorIds(prev =>
            prev.includes(id)
                ? prev.filter(x => x !== id)
                : [...prev, id]
        );
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError("");
        setSuccess("");

        if (!name.trim()) {
            setError("Name is required");
            return;
        }

        if (selectedSectorIds.length === 0) {
            setError("At least one sector must be selected");
            return;
        }

        if (!agree) {
            setError("User must agree to terms");
            return;
        }


        const response = await fetch(`${API_BASE}/user-sectors`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                username: name,
                agreeToTerms: agree,
                sectorIds: selectedSectorIds
            })
        });

        if (!response.ok) {
            const msg = await response.text();
            setError(msg || "Failed to save data");
            return;
        }

        const data: UserSectorResponse = await response.json();
        setName(data.username);
        setAgree(data.agreeToTerms);
        setSelectedSectorIds(data.sectorIds);
        setSuccess("Saved successfully");
    };

    return (
        <div style={{ maxWidth: 600, margin: "2rem auto", fontFamily: "sans-serif" }}>
            <h2>Please enter your name and pick the Sectors you are currently involved in.</h2>

            <form onSubmit={handleSubmit}>
                <label>
                    <strong>Name</strong><br />
                    <input
                        type="text"
                        value={name}
                        onChange={e => setName(e.target.value)}
                        style={{ width: "100%", padding: "6px" }}
                    />
                </label>

                <br /><br />

                <strong>Sectors</strong>
                <div style={{ border: "1px solid #ccc", padding: "8px", maxHeight: 300, overflowY: "auto" }}>
                    {sectors.map(sector =>
                        <SectorNode
                            key={sector.id}
                            node={sector}
                            selected={selectedSectorIds}
                            onToggle={toggleSector}
                        />
                    )}
                </div>

                <br />

                <label>
                    <input
                        type="checkbox"
                        checked={agree}
                        onChange={e => setAgree(e.target.checked)}
                    />{" "}
                    Agree to terms
                </label>

                <br /><br />

                <button type="submit">Save</button>

                {error && <p style={{ color: "red" }}>{error}</p>}
                {success && <p style={{ color: "green" }}>{success}</p>}
            </form>
        </div>
    );
}
